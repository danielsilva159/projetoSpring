package curso.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Pessoa;
import curso.springboot.model.Telefone;
import curso.springboot.repository.PessoaRepository;
import curso.springboot.repository.TelefoneRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView inicio() {
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		andView.addObject("pessoabj", new Pessoa());
		Iterable<Pessoa> pessoaIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoaIt);
		return andView;
	}
	
	@PostMapping(value = "**/salvarpessoa")
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
			Iterable<Pessoa> pessoaIt = pessoaRepository.findAll();
			andView.addObject("pessoabj", pessoa);
			List<String> msg = new ArrayList<String>();
			for(ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}
			andView.addObject("msg", msg);
			return andView;
		}
		pessoaRepository.save(pessoa);
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoaIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoaIt);
		andView.addObject("pessoabj", new Pessoa());
		
		
		return andView;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/listapessoa")
	public ModelAndView pessoas() {
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoaIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoaIt);
		andView.addObject("pessoabj", new Pessoa());
		return andView;
	}
	
	@GetMapping("**/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		andView.addObject("pessoabj", pessoa.get());
		andView.addObject("telefones",telefoneRepository.getTelefones(idpessoa));
		return andView;
		
	}
	@GetMapping("**/removerpessoa/{idpessoa}")
	public ModelAndView excluir(@PathVariable("idpessoa") Long idpessoa) {
		pessoaRepository.deleteById(idpessoa);
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		andView.addObject("pessoabj", pessoaRepository.findAll());
		andView.addObject("pessoabj", new Pessoa());
		return andView;
		
	}
	@PostMapping("**/pesquisarpessoa")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		andView.addObject("pessoas", pessoaRepository.findPessoaByName(nomepesquisa));
		andView.addObject("pessoabj", new Pessoa());
		return andView;
	}
	@GetMapping("/telefones/{idpessoa}")
	public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		ModelAndView andView = new ModelAndView("cadastro/telefones");
		andView.addObject("pessoabj", pessoa.get());
		andView.addObject("telefones",telefoneRepository.getTelefones(idpessoa));
		return andView;
		
	}
	
	@PostMapping("**/addfonePessoa/{pessoaid}")
	public ModelAndView addfonePessoa(Telefone telefone, @PathVariable("pessoaid") Long pessoaid) {
		
		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		telefone.setPessoa(pessoa);
		telefoneRepository.save(telefone);
		ModelAndView andView = new ModelAndView("cadastro/telefones");
		andView.addObject("pessoabj",pessoa);
		andView.addObject("telefones",telefoneRepository.getTelefones(pessoaid));
		return andView;
	}
	
	@GetMapping("/removertelefone/{idtelefone}")
	public ModelAndView removertelefone(@PathVariable("idtelefone") Long idtelefone) {
		Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();
		telefoneRepository.deleteById(idtelefone);
		ModelAndView andView = new ModelAndView("cadastro/telefones");
		andView.addObject("pessoabj", pessoa);
		andView.addObject("telefones",telefoneRepository.getTelefones(pessoa.getId()));
		return andView;
		
	}
}
